import os
import hashlib
import time

import psycopg2
from os import getenv
import sys

SCHEMA = "IAM"
IDENTITIES_TABLE_NAME = f"{SCHEMA}.identities"
USERS_TABLE_NAME = f"{SCHEMA}.users"
ROOT_USER_ID = getenv("ROOT_USER_ID", "00000000-0000-0000-0000-000000000001")
ROOT_USER_PASSWORD = getenv("ROOT_USER_PASSWORD", "root")
ROOT_USER_NAME = getenv("ROOT_USER_NAME", "root")
DB_PASSWORD = getenv("DB_PASSWORD")
DB_HOST = getenv("DB_HOST")
DB_NAME = getenv("DB_NAME")
DB_USER = getenv("DB_USER")
DB_PORT = getenv("DB_PORT", "5432")

def main():
    print("Hello from bootstrap!")
    try:
        print("Connecting to PostgreSQL database...")
        conn = psycopg2.connect(user=DB_USER, password=DB_PASSWORD, host=DB_HOST,
                                port=DB_PORT, database=DB_NAME)
        print("Connected!")
        cur = conn.cursor()
        cur.execute(f"SELECT * FROM {IDENTITIES_TABLE_NAME} i JOIN {USERS_TABLE_NAME} u ON i.id = u.id WHERE i.id=%s", (ROOT_USER_ID,))
        rows = cur.fetchall()
        if len(rows) == 1:
            print("Root User exists!")
        else:
            print("Root User does not exist!")
            salt = os.urandom(8).hex()
            hashed_password = hashlib.pbkdf2_hmac("sha256", ROOT_USER_PASSWORD.encode("utf-8"),
                                                  salt.encode("utf-8"), 10000).hex()
            curr_time = int(time.time())
            cur.execute(f"INSERT INTO {IDENTITIES_TABLE_NAME} (id, name, secret, salt, created_time, updated_time, type, status) VALUES (%s, %s, %s, %s, %s, %s, %s, %s)",
                        (ROOT_USER_ID, ROOT_USER_NAME, hashed_password.encode("utf-8").hex(),
                         salt, curr_time, curr_time, "USER", "ACTIVE"))
        conn.commit()
        conn.close()
    except psycopg2.Error as e:
        print(e)
        sys.exit(1)
    except Exception as e:
        print(e)
        sys.exit(2)


if __name__ == "__main__":
    main()
