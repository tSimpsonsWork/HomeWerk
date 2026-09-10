const signupForm = document.getElementById("signup-form");
const signupError = document.getElementById("signup-error");
const signupSuccess = document.getElementById("signup-success");

signupForm.addEventListener("submit", async (event) => {

    event.preventDefault();

    signupError.textContent = "";
    signupSuccess.textContent = "";

    const displayName =
        document.getElementById("display-name").value;

    const email =
        document.getElementById("email").value;

    const password =
        document.getElementById("password").value;

    const signupRequest = {
        displayName: displayName,
        email: email,
        password: password
    };

    try {

        const response = await fetch("/auth/signup", {
            method: "POST",

            headers: {
                "Content-Type": "application/json"
            },

            body: JSON.stringify(signupRequest)
        });

        if (!response.ok) {

            try {

                const errorResponse = await response.json();

                signupError.textContent =
                    errorResponse.message || "Unable to create account.";

            } catch (error) {

                signupError.textContent =
                    "Unable to create account.";

            }

            return;
        }

        signupSuccess.textContent =
            "Account created. Your account is waiting for approval.";

        signupForm.reset();

    } catch (error) {

        signupError.textContent =
            "Unable to connect to HoMeK. Please try again.";

    }

});