FROM python:3.13-slim

# Recommended for psycopg2-binary / SSL etc.
RUN apt-get update \
  && apt-get install -y --no-install-recommends ca-certificates \
  && rm -rf /var/lib/apt/lists/*

# Install uv into the image
RUN pip install --no-cache-dir uv

WORKDIR /app

# Copy only dependency metadata first for better layer caching
COPY pyproject.toml uv.lock ./

# Create venv + install deps from lockfile
RUN uv sync --frozen --no-dev

# Then copy the application code
COPY . .

# Run in the created environment
ENTRYPOINT ["uv", "run", "python", "main.py"]
