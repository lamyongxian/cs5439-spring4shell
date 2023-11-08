#!/bin/bash
name=$(python get_ip.py)
echo "::set-env name=ip_addr::$name"
