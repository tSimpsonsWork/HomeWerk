const loginForm = document.getElementById("login-form");
const loginError = document.getElementById("login-error");

loginForm.addEventListener("submit", async (event) => {

    event.preventDefault();

    loginError.textContent = "";

    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    const loginRequest = {
        email: email,
        password: password
    };

    try {

        const response = await fetch("/auth/login", {
            method: "POST",

            headers: {
                "Content-Type": "application/json"
            },

            body: JSON.stringify(loginRequest)
        });

        if (!response.ok) {
            loginError.textContent = "Invalid email or password.";
            return;
        }

        window.location.href = "/dashboard.html";

    } catch (error) {

        loginError.textContent =
            "Unable to connect to HoMeK. Please try again.";

    }

});