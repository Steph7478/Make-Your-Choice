#!/bin/bash

choicesURL=http://localhost:8080/choices
dialogURL=http://localhost:8080/dialog


while true; do
    echo -e "\n=== Testing Endpoints ===\n"
    echo "Choose an endpoint:"
    echo "1) GET /choices"
    echo "2) GET /dialog"
    echo "0) Exit"

    read -p "Option: " option

    case $option in
        1)
            while true; do
                echo -e "\n=== Testing GET /choices ===\n"
                echo "Choose a method:"
                echo "1) Test all endpoints"
                echo "2) Test specific endpoints"
                echo "0) Back"

                read -p "Option: " suboption

                case $suboption in
                    1)
                        echo -e "\n=== Testing all endpoints ==="
                        echo -e "\nFn:getAllChoices()"
                        echo "Path: /"
                        echo
                        curl $choicesURL/
                        echo
                        
                        echo -e "\nFn:getChoiceById()"
                        echo "Path: /choices/{code}"
                        read -p "Enter code: " code
                        echo
                        curl $choicesURL/$code
                        echo
                        
                        echo -e "\nFn:getNextChoiceByDialogCode()"
                        echo "Path: /choices/next/{nextDialogCode}"
                        read -p "Enter nextDialogCode: " nextDialogCode
                        echo
                        curl $choicesURL/next/$nextDialogCode
                        echo
                        ;;
                    2)
                        while true; do
                            echo -e "\n=== Testing endpoints manually ===\n"
                            echo "Select an endpoint:"
                            echo "1) /choices/"
                            echo "2) /choices/{code}"
                            echo "3) /choices/next/{nextDialogCode}"
                            echo "0) Back"

                            read -p "Option: " endpoint

                            case $endpoint in
                                1)
                                    echo -e "\n=== Testing all endpoints ==="
                                    echo -e "\nFn:getAllChoices()"
                                    echo "Path: /"
                                    echo
                                    curl $choicesURL/
                                    ;;
                                2)
                                    echo -e "\nFn:getChoiceById()"
                                    echo "Path: /choices/{code}"
                                    read -p "Enter code: " code
                                    echo
                                    curl $choicesURL/$code
                                    ;;
                                3)
                                    echo -e "\nFn:getNextChoiceByDialogCode()"
                                    echo "Path: /choices/next/{nextDialogCode}"
                                    read -p "Enter nextDialogCode: " nextDialogCode
                                    echo
                                    curl $choicesURL/next/$nextDialogCode
                                    ;;
                                0)
                                    echo -e "\nReturning to main menu..."
                                    break
                                    ;;
                                *)
                                    echo -e "\nInvalid option!"
                                    ;;
                            esac
                        done
                        ;;
                    0)
                        echo -e "\nReturning..."
                        break
                        ;;
                    *)
                        echo -e "\nInvalid option!"
                        ;;
                esac
            done
            ;;
        2)
            while true; do
                echo -e "\n=== Testing GET /dialog ===\n"
                echo "Choose a method:"
                echo "1) Test all endpoints"
                echo "2) Test specific endpoints"
                echo "0) Back"

                read -p "Option: " suboption

                case $suboption in
                    1)
                        echo -e "\n=== Testing all endpoints ==="
                        echo -e "\nFn:getAllDialogs()"
                        echo "Path: /"
                        echo
                        curl $dialogURL/
                        echo
                        
                        echo -e "\nFn:getDialogByCodeUseCase()"
                        echo "Path: /dialog/{dialogCode}"
                        read -p "Enter dialogCode: " dialogCode
                        echo
                        curl $dialogURL/$dialogCode
                        echo
                        
                        echo -e "\nFn:getChoicesByDialogCode()"
                        echo "Path: /dialog/choices/{code}"
                        read -p "Enter code: " code
                        echo
                        curl $dialogURL/choices/$code
                        echo
                        ;;
                    2)
                        while true; do
                            echo -e "\n=== Testing endpoints manually ===\n"
                            echo "Select an endpoint:"
                            echo "1) /dialog/"
                            echo "2) /dialog/{dialogCode}"
                            echo "3) /dialog/choices/{code}"
                            echo "0) Back"

                            read -p "Option: " endpoint

                            case $endpoint in
                                1)
                                    echo -e "\n=== Testing all endpoints ==="
                                    echo -e "\nFn:getAllDialogs()"
                                    echo "Path: /"
                                    echo
                                    curl $dialogURL/
                                    ;;
                                2)
                                    echo -e "\nFn:getDialogByCodeUseCase()"
                                    echo "Path: /dialog/{dialogCode}"
                                    read -p "Enter dialogCode: " dialogCode
                                    echo
                                    curl $dialogURL/$dialogCode
                                    ;;
                                3)
                                    echo -e "\nFn:getChoicesByDialogCode()"
                                    echo "Path: /dialog/choices/{code}"
                                    read -p "Enter code: " code
                                    echo
                                    curl $dialogURL/choices/$code
                                    ;;
                                0)
                                    echo -e "\nReturning to main menu..."
                                    break
                                    ;;
                                *)
                                    echo -e "\nInvalid option!"
                                    ;;
                            esac
                        done
                        ;;
                    0)
                        echo -e "\nReturning..."
                        break
                        ;;
                    *)
                        echo -e "\nInvalid option!"
                        ;;
                esac
            done
            ;;
        0)
            echo -e "\nExiting..."
            exit 0
            ;;
        *)
            echo -e "\nInvalid option!"
            ;;
    esac
done
