server {
  listen 80;
  server_name eise.com;

  location / {
   proxy_pass http://127.0.0.1:3000/;
  }

  location /api/ {
    proxy_pass http://127.0.0.1:8080/;
  }
}

