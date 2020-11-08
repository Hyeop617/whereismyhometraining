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
      newCell.innerHTML = data[i].id
      newCell = newRow.insertCell(newRow.length)
      newCell.innerHTML = `<input class="form-control" type="text" placeholder="날짜" style="width: 50px" value="${data[i].day}">`;
      newCell = newRow.insertCell(newRow.length);
      newCell.innerHTML = `<input class="form-control" type="text"  placeholder="순서" style="width: 50px" value="${data[i].workoutOrder}">`;
      newCell = newRow.insertCell(newRow.length);
      newCell.innerHTML = `<input class="invisible" type="text" placeholder="세트수" style="width: 1px" value="${data[i].workoutId}" readonly>${data[i].workoutTitle}`;
      newCell = newRow.insertCell(newRow.length);
      newCell.innerHTML = `<input class="form-control" type="text" placeholder="세트수" style="width: 60px" value="${data[i].workoutSet}">`;
      newCell = newRow.insertCell(newRow.length);
      newCell.innerHTML = `<input class="form-control" type="text"  placeholder="횟수" style="width: 60px" value="${data[i].workoutCount}">`;
      newCell = newRow.insertCell(newRow.length);
      newCell.innerHTML = `<input class="form-control" type="text" placeholder="시간 (초)" style="width: 70px" value="${data[i].workoutTime}">`;
      newCell = newRow.insertCell(newRow.length);
      newCell.innerHTML = `<button class="btn btn-sm btn-danger" onclick="remove(`+ data[i].id +`, this)">삭제</button>`;
      newCell = newRow.insertCell(newRow.length);
      newCell.innerHTML = `<button class="btn btn-sm btn-secondary" onclick="modify(`+ data[i].id +`, this)">수정</button>`;
    }
  })
}

async function modify(id, node){
  const tr = node.parentNode.parentNode;
  console.log(tr);
  console.log(tr.children[3].children[0].value);
  const courseId = document.querySelector("[name=course]").value;

  const dto = {
    id :id,
    courseId: courseId,
    workoutId:tr.children[3].children[0].value,
    day:tr.children[1].children[0].value,
    workoutSet:tr.children[4].children[0].value,
    workoutCount: tr.children[5].children[0].value,
    workoutOrder:tr.children[2].children[0].value,
    workoutTime:tr.children[6].children[0].value,
  }
  console.log(dto)
  const response = await (await fetch("/admin/course/modify", {
    method: 'post',
    headers: {
      'Content-Type' : 'application/json'
    },
    body : JSON.stringify(dto)
  }))
  courseChange()
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
  const workoutTitle = document.querySelector("[name=newWorkout] option:checked").innerText;

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
    newCell.innerHTML = `${data}`
    newCell = newRow.insertCell(newRow.length);
    newCell.innerHTML = `<input class="form-control" type="text" placeholder="날짜" style="width: 50px" value="${day}">`;
    newCell = newRow.insertCell(newRow.length);
    newCell.innerHTML = `<input class="form-control" type="text" placeholder="순서" style="width: 50px" value="${workoutOrder}">`;
    newCell = newRow.insertCell(newRow.length);
    newCell.innerHTML = `<input  class="invisible" type="text" placeholder="세트수" style="width: 1px" value="${workoutId}" readonly>${workoutTitle}`;
    newCell = newRow.insertCell(newRow.length);
    newCell.innerHTML = `<input class="form-control" type="text" placeholder="세트수" style="width: 60px" value="${workoutSet}">`;
    newCell = newRow.insertCell(newRow.length);
    newCell.innerHTML = `<input class="form-control" type="text"  placeholder="횟수" style="width: 60px" value="${workoutCount}">`;
    newCell = newRow.insertCell(newRow.length);
    newCell.innerHTML = `<input class="form-control" type="text" placeholder="시간 (초)" style="width: 70px" value="${workoutTime}">`;
    newCell = newRow.insertCell(newRow.length);
    newCell.innerHTML = `<button class="btn btn-sm btn-danger" onclick="remove(${data}, this)">삭제</button>`;
    newCell = newRow.insertCell(newRow.length);
    newCell.innerHTML = `<button class="btn btn-sm btn-secondary" onclick="modify(${data}, this)">수정</button>`;
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

