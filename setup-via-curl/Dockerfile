FROM alpine

RUN apk --no-cache add curl

COPY ./curl-setup.sh curl-setup.sh

CMD ["sh", "./curl-setup.sh"]
