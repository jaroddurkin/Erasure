FROM itzg/minecraft-server:latest

ENV TYPE=SPIGOT
ENV EULA=TRUE

WORKDIR /data
RUN mkdir plugins
RUN chmod -R 777 /data
COPY ./target/Erasure.jar plugins

CMD [/start]
