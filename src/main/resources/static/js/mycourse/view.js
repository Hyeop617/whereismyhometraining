let currentSet = 1;
let isPause = false;
let setFinish = false;      // 한 세트 끝나고 쉬는시간인지 판별
let isAlert = true;         // 운동 시작 전 15초 인지 판별
let nextWorkoutYet = true     // 타이머다 끝내고 다음 운동 넘어가는지 판

window.onload = function () {
  console.log(workoutTime)
}
async function timerCheck() {
  const timerBeep = new Audio("/sound/timer_beep.wav")
  const workout_start = new Audio("/sound/workout_start.wav")
  const workout_end = new Audio("/sound/workout_end.wav")
  const set_finish = new Audio("/sound/set_finish.wav")
  const breaktime = new Audio("/sound/breaktime.wav")
  const start_sound = new Audio("/sound/start.m4a")
  const one = new Audio("/sound/1.m4a")
  const two = new Audio("/sound/2.m4a")
  const three = new Audio("/sound/3.m4a")

  if(currentSet == 1){
    workout_start.play()
  }
  if (workoutTime > 0 && nextWorkoutYet) {
    const startButton = document.querySelector("[name=startButton]");
    const intervalAlert = document.querySelector("[name=intervalAlert]");
    intervalAlert.classList.remove('invisible');
    startButton.style.pointerEvents = 'none';
    let tempTime = 15            // 준비시간

    let interval = setInterval(function () {
      if(isAlert) {
        startButton.textContent = `${parseInt(tempTime / 60)}:${tempTime % 60}`
        startButton.style.background = tempTime % 2 == 1 ? 'rgba(0,0,0,1)' : 'rgba(255,255,255,1)'
        startButton.style.color = tempTime % 2 == 1 ? 'rgba(255,255,255,1)' : 'rgba(0,0,0,1)'
        startButton.style.fontSize = '40px'
        tempTime--;

        if(tempTime == 0){
          one.play()
          setTimeout(function (){
            start_sound.play()
          }, 1000)
        }
        if(tempTime == 1){
          two.play()
        }
        if(tempTime == 2){
          three.play()
        }
        if(tempTime < 0){
          isAlert = false
          tempTime = workoutTime
        }
      } else {
        intervalAlert.classList.add('invisible');
        if(tempTime >= 0 &&!setFinish){                                                   // 타이머 돌아감(운동중)
          timerBeep.play();
          startButton.style.background = tempTime % 2 == 1 ? 'rgba(0,0,0,1)' : 'rgba(255,255,255,1)'
          startButton.style.color = tempTime % 2 == 1 ? 'rgba(255,255,255,1)' : 'rgba(0,0,0,1)'
          startButton.style.fontSize = '40px'
        }
        startButton.textContent = `${parseInt(tempTime / 60)}:${tempTime % 60}`
        tempTime--
        if(setFinish){
          if(tempTime == 0){
            one.play()
            setTimeout(function (){
              start_sound.play()
            }, 1000)
          }
          if(tempTime == 1){
            two.play()
          }
          if(tempTime == 2){
            three.play()
          }
        }

        if(tempTime < 0 && !setFinish && currentSet != totalSet){                                     // 쉬는시간
            console.log("쉬는시간이에요")
            set_finish.play().then(() => {
              setTimeout(function (){
                if(currentSet <= totalSet){
                  breaktime.play();
                }
              }, 1650)
            })
            if(currentSet != totalSet){
                set_finish.play()

                tempTime = 60                                              // 쉬는시간
                startButton.style.background = 'rgba(136,176,75,1)'
                startButton.style.color = 'rgba(255,255,255,1)'

            }
            currentSet++;
            setFinish = true
        } else if (tempTime < 0 && !setFinish && currentSet == totalSet) {
          clearInterval(interval);
          workout_end.play()
          startButton.style.fontSize = '20px'
          startButton.style.background = 'rgba(240,112,218,1)'
          startButton.style.pointerEvents = '';
          startButton.textContent = '다음 운동!'
          nextWorkoutYet = false
          currentSet++
          console.log("timer end.")
        } else if (tempTime < 0 && setFinish) {                          // 세트 시작
          console.log(`다시 하세요 현재 ${currentSet}세트`)
          tempTime = workoutTime;
          setFinish = false;
        }
      }
    }, 1000);

  } else {
    await start()
  }
}

async function start() {
  const startButton = document.querySelector("[name=startButton]");
  const fanfare = new Audio("/sound/fanfare.wav")

  if (currentSet > totalSet) {
    startButton.style.background = 'rgba(240,112,218,1)'
    startButton.textContent = '다 했어요!'
    nextWorkoutYet = true
    workoutOrder++;
    await nextWorkout(workoutOrder);
  } else if (currentSet == totalSet) {
    startButton.style.background = 'rgba(240,112,218,1)'
    startButton.textContent = '다음 운동!'
  } else {
    startButton.style.background = 'rgba(176,112,240,1)'
    startButton.textContent = `${currentSet} 세트 끝!`
  }
  setFinish = false;
  isAlert = true;
  // nextWorkoutYet = true
  currentSet++;
}


async function nextWorkout(workoutOrder) {
  const startButton = document.querySelector("[name=startButton]");
  const workoutTitle = document.querySelector("[name=workoutTitle]");
  const workoutCountSet = document.querySelector("[name=workoutCountSet]");
  const workoutImgPath = document.querySelector("[name=workoutImgPath]");
  const workoutDescrtipion = document.querySelector("[name=workoutDescrtipion]");
  const workoutYoutubePath = document.querySelector("[name=workoutYoutubePath]");
  const progressBar = document.querySelector("[name=progressBar]");
  const fanfare = new Audio("/sound/fanfare.wav")


  const dto = {
    courseId: courseId,
    day: day,
    workoutOrder: workoutOrder
  }
  const response = await (await fetch('/mycourse/nextWorkout', {
    method: 'post',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(dto)
  })).json()
  if (response.data == 'totalFinish') {
    progressBar.style.width = '100%'
    startButton.classList.add('d-none')
    fanfare.play()
    alert("코스를 모두 완료했어요!!!")
  } else if (response.data == 'todayFinish') {
    progressBar.style.width = '100%'
    startButton.classList.add('d-none')
    alert("오늘 운동을 완료했어요!")
  } else {
    workoutTitle.textContent = response.data.workoutTitle;
    workoutCountSet.textContent = response.data.workoutTime > 600 ? `${response.data.workoutTime / 60}분 ${response.data.workoutSet}세트`
      : response.data.workoutTime > 0 ? `${response.data.workoutTime}초 ${response.data.workoutSet}세트`
        : `${response.data.workoutCount}회 ${response.data.workoutSet}세트`
    workoutImgPath.textContent = response.data.workoutImgPath
    workoutDescrtipion.textContent = response.data.workoutDescription
    workoutYoutubePath.setAttribute("src", response.data.workoutYoutubePath)
    progressBar.style.width = `${response.data.progressDay}%`
    totalSet = response.data.workoutSet
    currentSet = 0;
  }

  startButton.style.background = 'rgba(112,169,240,1)'
  startButton.textContent = '운동 시작'
}


async function getDetail(order) {
  const workoutTitle = document.querySelector("[name=workoutTitle]");
  const workoutCountSet = document.querySelector("[name=workoutCountSet]");
  const workoutImgPath = document.querySelector("[name=workoutImgPath]");
  const workoutDescrtipion = document.querySelector("[name=workoutDescrtipion]");
  const workoutYoutubePath = document.querySelector("[name=workoutYoutubePath]");
  const prevButton = document.querySelector("[name=prevButton]");
  const nextButton = document.querySelector("[name=nextButton]");

  const dto = {
    courseId: courseId,
    day: day,
    order: order
  }

  let response = await (await fetch('/course/detail/', {
    method: 'post',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(dto)
  })).json()

  workoutTitle.textContent = response.workoutTitle;
  workoutCountSet.textContent = response.workoutTime > 600 ? `${response.workoutTime / 60}분 ${response.workoutSet}세트`
    : response.workoutTime > 0 ? `${response.workoutTime}초 ${response.workoutSet}세트`
      : `${response.workoutCount}회 ${response.workoutSet}세트`
  workoutImgPath.textContent = response.workoutImgPath
  workoutDescrtipion.textContent = response.workoutDescription
  workoutYoutubePath.setAttribute("src", response.workoutYoutubePath)
}