FROM node:alpine

RUN npm config set fund false && \
    npm install -g npm && \
    npm install -g dynamodb-admin

EXPOSE 8001

CMD [ "dynamodb-admin" ]
