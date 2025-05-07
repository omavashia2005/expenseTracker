import streamlit as st
import requests

if not st.session_state["AuthToken"]:
    st.subheader("Please log in!")
    st.page_link(page="Homepage.py", label="Login/Register")

else:
    headers = {
        "Authorization": f"Bearer {st.session_state["AuthToken"]}"
    }

    BASE_URL = "http://localhost:8080/api/categories"
    action = st.radio("Choose Action", ["Add Category", "Update Category", "Find Category By ID", "All My Categories"])

    if action == "Add Category":
        title = st.text_input("Title")
        description = st.text_input("Description")
        payload = {
            "title": title,
            "description": description
        }
        if st.button("Submit"):
            res = requests.post(BASE_URL, json=payload, headers=headers)
            if res.status_code == 201:
                st.dataframe(res.json())
                st.session_state["CategoryID"] = res.json()["categoryID"]
                st.page_link(page="pages/Transactions.py", label="Add Expense?")
            else:
                error_data = res.json()
                st.error(f"{error_data.get('message')}")
                if error_data.get('message') == "Authorization header missing":
                    st.page_link(page="Homepage.py", label="Login")

    if action == "All My Categories":
        if st.button("Get My Categories"):
            res = requests.get(BASE_URL, headers=headers)

            if res.status_code == 200:
                st.dataframe(res.json())

    if action == "Find Category By ID":
        catID = st.text_input("Category ID")
        if st.button("Get Category"):
            res = requests.get(BASE_URL + "/" + catID, headers=headers)
            if res.status_code == 200:
                st.dataframe(res.json())
            else:
                error_data = res.json()
                st.error(f"{error_data.get('message')}")

    if action == "Update Category":

        catID = st.text_input("Enter Category ID")
        title = st.text_input("Title")
        description = st.text_input("Description")

        payload = {
            "title": title,
            "description": description
        }

        if st.button("Update Category"):
            res = requests.put(BASE_URL + "/" + catID, headers=headers, json=payload)
            if res.status_code == 200:
                if res.json()["success"]:  st.write("Update Successful")
                st.session_state["CategoryID"] = catID
                st.page_link(page="pages/Transactions.py", label="Update Expense?")
            else:
                error_data = res.json()
                st.error(f"{error_data.get('message')}")

st.page_link(page="pages/Transactions.py", label="Transactions")
st.page_link(page="Homepage.py", label="Login/Register")

if st.button("Logout"):
    st.session_state["AuthToken"] = None
    st.success("Logged out successfully.")
    st.switch_page("Homepage.py")