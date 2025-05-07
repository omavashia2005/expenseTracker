import streamlit as st
import requests
import datetime
import pandas as pd

if not st.session_state["AuthToken"]:
    st.subheader("Please log in!")
    st.page_link(page="Homepage.py", label="Login/Register")

else:
    headers = {
        "Authorization": f"Bearer {st.session_state["AuthToken"]}"
    }

    BASE_URL = "http://localhost:8080/api/categories"
    action = st.radio("Choose Action", ["Add Transaction", "Update Transaction", "Find Transaction By ID", "All Transactions"])


    if action == "Add Transaction":

        amount = st.number_input("Enter Amount")
        note = st.text_input("Add note")
        date = st.date_input("Enter date")
        dt = datetime.datetime.combine(date, datetime.time.min)
        timestamp_seconds = int(dt.timestamp() * 1000)

        if st.button("Upload Transaction"):
            payload = {
                "Amount" : amount,
                "note" : note,
                "transactionDate": timestamp_seconds
            }

            res = requests.post(f"{BASE_URL}/{st.session_state["CategoryID"]}/transactions", json=payload, headers=headers)

            if res.status_code == 201:
                df = pd.DataFrame([res.json()])
                df['transactionDate'] = pd.to_datetime(df["transactionDate"], unit='ms').dt.strftime("%m/%d/%Y")
                st.dataframe(df.transpose())
            else:
                error_data = res.json()
                st.error(f"{error_data.get('message')}")

    if action == "All Transactions":
        res = requests.get(f"{BASE_URL}/{st.session_state["CategoryID"]}/transactions", headers=headers)

        if res.status_code == 302:
            df = pd.DataFrame(res.json())
            df['transactionDate'] = pd.to_datetime(df["transactionDate"], unit='ms').dt.strftime("%m/%d/%Y")
            st.dataframe(df)
        else:
            error_data = res.json()
            st.error(f"{error_data.get('message')}")

    if action == "Find Transaction By ID":

        transactionID = st.text_input("Enter transaction ID")
        catID = st.text_input("Enter category ID")
        if st.button(f"Find Transaction with ID"):

            res = requests.get(f"{BASE_URL}/{catID}/transactions/{transactionID}", headers=headers)
            if res.status_code == 302:
                df = pd.DataFrame([res.json()])
                df['transactionDate'] = pd.to_datetime(df["transactionDate"], unit='ms').dt.strftime("%m/%d/%Y")
                st.dataframe(df.transpose())
            else:
                error_data = res.json()
                st.error(f"{error_data.get('message')}")
    if action == "Update Transaction":
        st.header("Coming soon")


st.page_link(page="pages/Categories.py", label="Categories")
st.page_link(page="Homepage.py", label="Login/Register")