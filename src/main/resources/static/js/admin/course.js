window.onload = function () {
  courseChange()
  keyEvent()
}

courseChange = function () {
  const id = document.querySelector("[name=course]").value;
  const tbody = document.querySelector('tbody');

  fetch("/admin/course/" + id, {
    method: 'post',
    headers: {
      'Content-Type': 'application/json'
    }
  }).then(res => {
    return res.json()
  }).then(data => {
    let element = tbody.querySelectorAll("tr:not(.addRow)");
    element.forEach(function (e){
      e.innerHTML = '';
    })
    for (let i = 0; i < data.length; i++) {
      let newRow = tbody.insertRow(0);
      let newCell = newRow.insertCell(newRow.length);
      newCell.innerHTML = data[i].day;
      newCell = newRow.insertCell(newRow.length);
      newCell.innerHTML = data[i].workoutOrder;
      newCell = newRow.insertCell(newRow.length);
      newCell.innerHTML = data[i].workoutTitle;
      newCell = newRow.insertCell(newRow.length);
      newCell.innerHTML = data[i].workoutSet;
      newCell = newRow.insertCell(newRow.length);
      newCell.innerHTML = data[i].workoutCount;
      newCell = newRow.insertCell(newRow.length);
      newCell.innerHTML = data[i].workoutTime;
      newCell = newRow.insertCell(newRow.length);
      newCell.innerHTML = `<button onclick="remove(`+ data[i].id +`, this)">삭제</button>`;
    }
  })
}

function keyEvent(){
  window.addEventListener("keydown", e => {
    if(e.key == 'Enter'){
      add()
    }
  })
}

add = function () {
  const workoutId = document.querySelector("[name=newWorkout]").value;
  const courseId = document.querySelector("[name=course]").value;
  const day = document.querySelector("[name=newDay]").value;
  const workoutOrder = document.querySelector("[name=newOrder]").value;
  const workoutSet = document.querySelector("[name=newSet]").value;
  const workoutCount = document.querySelector("[name=newCount]").value;
  const workoutTime = document.querySelector("[name=newTime]").value;
  const tbody = document.querySelector('tbody');

  const dto = {
    workoutId : workoutId,
    courseId : courseId,
    day : day,
    workoutOrder : workoutOrder,
    workoutSet : workoutSet,
    workoutCount : workoutCount,
    workoutTime : workoutTime
  }

  fetch("/admin/course/add", {
    method : 'post',
    headers: {
      'Content-Type': 'application/json'
    },
    body : JSON.stringify(dto)
  }).then(res => {
    return res.json();
  }).then(data => {
    console.log(data)
    let newRow = tbody.insertRow(tbody.childElementCount-1);
    let newCell = newRow.insertCell(newRow.length);
    newCell.innerHTML = day;
    newCell = newRow.insertCell(newRow.length);
    newCell.innerHTML = workoutOrder;
    newCell = newRow.insertCell(newRow.length);
    newCell.innerHTML = document.querySelector("[name=newWorkout] option:checked").innerText;
    newCell = newRow.insertCell(newRow.length);
    newCell.innerHTML = workoutSet;
    newCell = newRow.insertCell(newRow.length);
    newCell.innerHTML = workoutCount;
    newCell = newRow.insertCell(newRow.length);
    newCell.innerHTML = workoutTime;
    newCell = newRow.insertCell(newRow.length);
    newCell.innerHTML = `<button onclick="remove(${data}, this)">삭제</button>`;
  })
}

remove = function (id, node){
  console.log(id)
  const tr = node.parentNode.parentNode;
  fetch("/admin/course/delete", {
    method : 'post',
    headers: {
      'Content-Type': 'application/json'
    },
    body : JSON.stringify(id)
  }).then(res => {
    console.log(res)
    tr.innerHTML = '';
  })
}

