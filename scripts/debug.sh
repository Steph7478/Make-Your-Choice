#!/bin/bash

echo "=== Spring Boot Debug Menu ==="
echo -e "\nChoose log level:"
echo "1) TRACE"
echo "2) DEBUG"
echo "3) INFO"
echo "4) WARN"
echo "5) ERROR"
echo "0) Exit"

read -p "Option: " option

case $option in
    1) LOG_LEVEL="TRACE" ;;
    2) LOG_LEVEL="DEBUG" ;;
    3) LOG_LEVEL="INFO" ;;
    4) LOG_LEVEL="WARN" ;;
    5) LOG_LEVEL="ERROR" ;;
    0) echo "Exiting..."; exit 0 ;;
    *) echo "Invalid option!"; exit 1 ;;
esac

echo -e "\nChoose profile:"
echo "1) test"
echo "2) dev"
echo "3) prod"
echo "4) other"
read -p "Option: " prof_option

case $prof_option in
    1) PROFILE="test" ;;
    2) PROFILE="dev" ;;
    3) PROFILE="prod" ;;
    4) read -p "Enter profile name: " PROFILE ;;
    *) echo "Invalid profile!"; exit 1 ;;
esac

clear
echo "=== Running Spring Boot with log level $LOG_LEVEL and profile $PROFILE ==="
echo "Logs will appear below:"
echo "----------------------------------------"

./mvnw spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=$PROFILE --debug" | grep "$LOG_LEVEL"
