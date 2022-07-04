document.getElementById("genBtn").addEventListener("click", () => {
  generatePassword();
});
document.getElementById("save").addEventListener("click", () => {
  savePassword();
});
document.getElementById("cancel").addEventListener("click", () => {
  cancel();
});

async function fetchData(url, requestType, body) {
  try {
    let res = await fetch(url, {
      method: requestType,
      headers: {
        "Content-Type": "application/json",
      },
      body: body,
    });
    let data = await res.json();
    return data;
  } catch (error) {
    console.log(error);
  }
}

async function generatePassword() {
  let companyName = document.getElementById("company").value;
  let length = companyName.length;
  while (length < 8) length *= 2;
  let data = await fetchData(
    `http://localhost:8080/passwords/generate?length=${length}`
  );
  let p = document.getElementById("password");
  p.innerText = data.password;
  console.log(data);
  return data;
}

async function savePassword() {
  let companyName = document.getElementById("company").value;
  let password = document.getElementById("password").innerText;
  let data = {
    companyName: companyName,
    password: password,
  };
  let res = await fetchData(
    "http://localhost:8080/passwords",
    "POST",
    JSON.stringify(data)
  );
  console.log(res);
}

function cancel() {
  let p = document.getElementById("password");
  p.innerText = "Cancel bhai Cancel...";
}
