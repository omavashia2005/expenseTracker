import streamlit as st
import requests

BASE_URL = "http://localhost:8080/api/users"

st.title("Expense Tracker")

# UI flow control
action = st.radio("Choose Action", ["Register", "Login"])

# Shared inputs
email = st.text_input("Email")
password = st.text_input("Password", type="password")

if action == "Register":
    firstName = st.text_input("First Name")
    lastName = st.text_input("Last Name")

    if st.button("Register"):
        payload = {
            "firstName": firstName,
            "lastName": lastName,
            "email": email,
            "password": password
        }
        res = requests.post(f"{BASE_URL}/register", json=payload)
        if res.status_code == 200:
            st.success("You are now registered!")
            st.session_state["AuthToken"] = res.json().get("token: ")
            print(st.session_state["AuthToken"])
        else:
            error_data = res.json()
            st.error(f"{error_data.get('message')}")


elif action == "Login":
    if st.button("Login"):
        payload = {
            "email": email,
            "password": password
        }
        res = requests.post(f"{BASE_URL}/login", json=payload)
        if res.status_code == 200:
            st.success("You are now logged in!")
            st.session_state["AuthToken"] = res.json().get("token")

        else:
            error_data = res.json()
            st.error(f"{error_data.get('message')}")
