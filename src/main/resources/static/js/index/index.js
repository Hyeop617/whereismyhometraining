window.onload = function () {
  if(isAnonymous === false){
    initChart()
  }
}

async function initChart() {
  const totalCaloriesInfo = document.querySelector('[name=totalCaloriesInfo]');
  let response = await (await fetch(`/calories/calc`, {
    method: 'post',
    headers: {
      'Content-Type': 'application/json'
    }
  })).json();
  console.log(response);
  let caloriesData = response.data;

  totalCaloriesInfo.textContent = `오늘의 총 소모 칼로리는 ${caloriesData.totalCalories} 입니다.`

  let ctx = document.querySelector('[name=caloriesChart]').getContext('2d');
  let chart = new Chart(ctx, {
    type: 'horizontalBar',
    data: {
      labels: ["상체", "하체", "복근", "전신"],
      datasets: [{
        label: "My First dataset",
        data: [caloriesData.upperCalories, caloriesData.lowerCalories, caloriesData.coreCalories, caloriesData.allCalories],
        backgroundColor: [
          'rgba(240,112,218, .5)','rgba(240,112,218, .5)','rgba(240,112,218, .5)','rgba(240,112,218, .5)',
        ],
        borderColor: [
          'rgba(200, 99, 132, .7)','rgba(200, 99, 132, .7)','rgba(200, 99, 132, .7)','rgba(200, 99, 132, .7)',
        ],
        borderWidth: 2
      }]
    },
    options: {
      responsive: true,
      legend: {
        display: false
      },
      "scales": {
        "xAxes": [{
          "ticks": {
            "beginAtZero": true
          }
        }]
      }
    }
  });
}