FROM fluent/fluentd:v1.13-1

# Install elasticsearch library and fluent plugin.
USER root
RUN apk add --no-cache --update --virtual .build-deps \
        sudo build-base ruby-dev \
 # Pin the elasticsearch-ruby library < 7.14.
 # See https://github.com/uken/fluent-plugin-elasticsearch/issues/912
 && sudo gem install fluent-plugin-elasticsearch \
 && sudo gem install fluent-plugin-input-gelf \
 && sudo gem sources --clear-all \
 && apk del .build-deps \
 && rm -rf /tmp/* /var/tmp/* /usr/lib/ruby/gems/*/cache/*.gem
USER fluent
