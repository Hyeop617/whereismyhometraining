window.onload = function () {
  initChart()
}

function initChart() {
  let ctx = document.querySelector('[name=caloriesChart]').getContext('2d');
  let chart = new Chart(ctx, {
    type: 'horizontalBar',
    data: {
      labels: ["상체", "하체", "복근"],
      datasets: [{
        label: "My First dataset",
        data: [65, 59, 90],
        backgroundColor: [
          'rgba(105, 0, 132, .2)',
        ],
        borderColor: [
          'rgba(200, 99, 132, .7)',
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