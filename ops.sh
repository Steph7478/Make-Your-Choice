#!/bin/bash

SCRIPTS_DIR="$(dirname "$0")/scripts"

case $1 in
  debug)
    "$SCRIPTS_DIR/debug.sh"
    ;;
  endpoints)
    "$SCRIPTS_DIR/test-endpoints.sh"
    ;;
  *)
    echo "Usage: ./ops.sh { debug | endpoints }"
    ;;
esac
