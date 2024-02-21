
<script>
document.getElementById("getDataButton").addEventListener("click", function() {
  fetch('https://api.example.com/data') // URL을 적절히 변경해야 합니다.
    .then(response => {
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      return response.json();
    })
    .then(data => {
      // 받아온 데이터를 처리합니다.
      displayData(data);
    })
    .catch(error => {
      console.error('There was a problem with the fetch operation:', error);
    });
});

function displayData(data) {
  // 받아온 데이터를 표시합니다.
  var dataContainer = document.getElementById("dataContainer");
  dataContainer.innerHTML = "<pre>" + JSON.stringify(data, null, 2) + "</pre>";
}
</script>
