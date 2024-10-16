#!/bin/bash

. ./scripts/parse_env.sh

mvn surefire-report:report

total="$(grep -h testsuite target/surefire-reports/TEST-*.xml | awk -F '"' '{sum+=$12} END {print sum}')"
error="$(grep -h testsuite target/surefire-reports/TEST-*.xml | awk -F '"' '{sum+=$14} END {print sum}')"
fail="$(grep -h testsuite target/surefire-reports/TEST-*.xml | awk -F '"' '{sum+=$18} END {print sum}')"

echo $error $fail $total 
# if [[ $error -ne 0 || $fail -ne 0 ]]; then
#     message=":red_circle:  *TEST CASE FAILING*  :red_circle: "
#     message="${message}Total: _*${total}*_, Error: _*${error}*_, Fail: _*${fail}*_."
#     message="${message} <https://hab-test-result.dev-polygontech.xyz/api/surefire-report.html|View Details>"

#     curl -X POST "https://slack.com/api/chat.postMessage" \
#         -H "accept: application/json" -d token="${TESTRESULT_SLACKBOT_TOKEN}" \
#         -d channel="${TESTRESULT_SLACK_CHANNEL_ID}" \
#         -d text="${message}" \
#         -d as_user=true
# fi
