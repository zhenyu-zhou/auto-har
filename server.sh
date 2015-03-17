echo "(server.sh) usage: sh server.sh username@server.com latency bandwidth"
remote="$1"
laten="$2"
bd="$3"
ssh $remote -t sudo sh \"/home/zzy/traffic.sh\" $laten $bd

remote="$4"
laten="$5"
bd="$6"
ssh $remote sh \"/home/zzy/traffic.sh\" $laten $bd

