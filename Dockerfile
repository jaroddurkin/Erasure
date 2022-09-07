FROM itzg/minecraft-server:latest

ENV TYPE=SPIGOT
ENV EULA=TRUE

WORKDIR /data
RUN mkdir plugins
COPY ./target/Erasure.jar plugins

CMD [/start]
