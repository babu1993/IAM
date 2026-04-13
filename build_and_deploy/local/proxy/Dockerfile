FROM docker.io/library/nginx:latest
COPY nginx.conf /etc/nginx/nginx.conf
#COPY certs/ /etc/nginx/certs/
RUN mkdir -p /var/nginx/client_body_temp \
    && chown -R nginx:nginx /var/nginx
EXPOSE 80
EXPOSE 443
ENTRYPOINT ["nginx", "-g", "daemon off;"]