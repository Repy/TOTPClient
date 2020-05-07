#!/bin/bash
mkdir -p "${HOME}/.totp"
cd "${HOME}/.totp"
"$(dirname $0)/image/bin/java" "-Xdock:icon=$(dirname $0)/../Resources/icon.png" -m "info.repy.totp/info.repy.totp.TOTPClient"
