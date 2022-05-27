FROM fedora:36

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
