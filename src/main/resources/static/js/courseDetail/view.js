window.onload = function () {

}

async function addUserCourse(courseId) {
  const dto = {
    courseId : courseId,
    day : 1,
    workoutOrder : 1
  }
  let response = await fetch('/mycourse/add', {
    method: 'post',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(dto)
  })

  if(response.status == 200) {
    alert("추가되었습니다.")
  } else if (response.status == 409) {
    alert("이미 추가된 코스입니다.")
  } else if (response.status == 401) {
    alert("대상 성별이 아닙니다!")
  }
  console.log(response)
}

async function getDetail(order) {
  console.log(order)
  const day = document.querySelector("[name=selectDay]").value;
  const workoutTitle = document.querySelector("[name=workoutTitle]");
  const workoutCountSet = document.querySelector("[name=workoutCountSet]");
  const workoutImgPath = document.querySelector("[name=workoutImgPath]");
  const workoutDescrtipion = document.querySelector("[name=workoutDescrtipion]");
  const workoutYoutubePath = document.querySelector("[name=workoutYoutubePath]");
  const prevButton = document.querySelector("[name=prevButton]");
  const nextButton = document.querySelector("[name=nextButton]");

  const dto = {
    courseId : courseId,
    day : day,
    order : order
  }

  let response = await (await fetch('/course/detail/', {
    method: 'post',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(dto)
  })).json()

  workoutTitle.textContent =  response.workoutTitle;
  workoutCountSet.textContent =  response.workoutTime > 600 ? `${response.workoutTime / 60}분 ${response.workoutSet}세트`
                                                            : response.workoutTime > 0  ? `${response.workoutTime}초 ${response.workoutSet}세트`
                                                                                        : `${response.workoutCount}회 ${response.workoutSet}세트`
  workoutImgPath.setAttribute("src", response.workoutImgPath)
  workoutDescrtipion.textContent =  response.workoutDescription
  workoutYoutubePath.setAttribute("src", response.workoutYoutubePath)

  console.log(response)

  if(response.isFirst){
    prevButton.classList.add('invisible');
  } else {
    prevButton.classList.remove('invisible');
  }

  if (response.isEnd){
    nextButton.classList.add('invisible');
  } else {
    nextButton.classList.remove('invisible');
  }
}