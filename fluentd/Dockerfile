FROM fluent/fluentd:v1.6.3-debian-1.1
USER root
RUN ["ruby", "--version"]
RUN ["gem", "install", "fluent-plugin-input-gelf", "--version", "0.3.1"]
RUN ["gem", "install", "fluent-plugin-elasticsearch", "--version", "3.5.0"]
