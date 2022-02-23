FROM fedora:35

RUN dnf install -y \
binutils \
gdb \
git \
perf \
perl-open

RUN cd /opt \
&& git clone https://github.com/brendangregg/FlameGraph

ENV FG_HOME /opt/FlameGraph

WORKDIR /data
