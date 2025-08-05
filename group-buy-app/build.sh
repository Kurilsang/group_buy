#!/bin/bash

# 启动脚本

# docker buildx build --load --platform liunx/amd64,linux/arm64 -t group-buy-app:1.0 -f ./Dockerfile . --push

# 普通镜像构建，随系统版本构建 amd/arm
docker build -t system/group-buy-app:1.0-SNAPSHOT -f ./Dockerfile .