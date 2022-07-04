//to get all password and append
appendData();

var id;

//delete the row
document.getElementById("delete").addEventListener("click", (e) => {
  e.preventDefault();
  deleteRow();
  location.reload();
});

//edit the row and update the row , // time out edit to prevent from null pointer exception
document.getElementById("edit").addEventListener("click", async (e) => {
  e.preventDefault();
  id = await editRow();
  setTimeout(() => {
    document
      .getElementById("web-input")
      .addEventListener("keypress", async (event) => {
        if (event.key == "Enter") {
          event.preventDefault();
          await updateRow();
          location.reload();
        }
      });
  }, 100);
});

//select all checkbox
document.getElementById("checkbox-all").addEventListener("click", () => {
  let selected = document.getElementsByClassName("checkbox");
  Array.from(selected).forEach((el) => {
    if (el.checked) el.checked = false;
    else el.checked = true;
  });
});

//to select single checkbox
let selected = document.getElementsByClassName("checkbox");
for (let i = 0; i < selected.length; i++) {
  el.addEventListener("click", deleteRow());
}

//to fetch the data
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

async function appendData() {
  let data = await fetchData(`http://localhost:8080/passwords`, "GET");
  // console.log(data);
  let p_div = document.getElementById("table");
  data.forEach((el) => {
    let row = document.createElement("div");
    row.className = "row";
    row.setAttribute("data-id", el.id);
    row.innerHTML = `<div class="check-col column">
            <input type="checkbox" class="checkbox" value="${el.id}">
        </div>
        <div class="website column"><p class="website-in">${el.companyName}</p>
        <input type="text" class="website-in hide" placeholder="Enter new company name">
        </div>
        <div class="password column">${el.password}</div>`;

    p_div.append(row);
  });
  return data;
}

//return all the index of all checkbox
function getChecked() {
  let selected = document.getElementsByClassName("checkbox");
  let array = [];
  Array.from(selected).forEach((el) => {
    if (el.checked) array.push(el.value);
  });
  return array;
}

//delete the rows checked
async function deleteRow() {
  let arr = getChecked();
  let url = "http://localhost:8080/passwords?list=";
  for (let i = 0; i < arr.length; i++) {
    let id = arr[i];
    if (i == arr.length - 1) url += id;
    else url += id + ",";
  }
  let list = await fetchData(url, "DELETE");
  console.log(list);
}

//edit the checked row
async function editRow() {
  let arr = getChecked();
  if (arr.length > 1) {
    window.alert("Please select only one company!");
    return;
  }
  let data = await fetchData(`http://localhost:8080/passwords`, "GET");
  let body;
  data.forEach((el) => {
    if ((el.id = arr[0])) body = el;
  });
  if (body) {
    let row = document.querySelector(`[data-id="${arr[0]}"]`);
    for (let i = 0; i < row.children.length; i++) {
      let node = row.children[i];
      if (node.getAttribute("class")[0] == "w") {
        node.children[0].classList.toggle("hide");
        node.children[1].classList.toggle("hide");
        node.children[1].id = "web-input";
      }
    }
  }
  return arr[0];
}

//generate password randomly
async function generatePassword() {
  let companyName = document.getElementById("web-input").value;
  let length = companyName.length;
  while (length < 8) length *= 2;
  let data = await fetchData(
    `http://localhost:8080/passwords/generate?length=${length}`
  );
  data.companyName = companyName;
  return data;
}

//Update the row
async function updateRow() {
  let password = await generatePassword();
  password.id = id;
  console.log(password);
  let updatedPassword = await fetchData(
    "http://localhost:8080/passwords",
    "PUT",
    JSON.stringify(password)
  );
  return updatedPassword;
}
