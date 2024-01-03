
document.addEventListener("DOMContentLoaded", function () {
    
    fetch('/analises/eventos-por-categoria')
        .then(response => response.json())
        .then(data => {
            // Crie um array de labels e um array de dados
            const labels = data.map(entry => Object.keys(entry)[0]);
            const values = data.map(entry => Object.values(entry)[0]);

            // Crie um gráfico de barras
            createBarChart('eventsByCategoryChart', 'Número de Eventos por Categoria', labels, values);
        });


    fetch('/analises/eventos-por-acao')
        .then(response => response.json())
        .then(data => {
            // Crie um array de labels e um array de dados
            const labels = data.map(entry => Object.keys(entry)[0]);
            const values = data.map(entry => Object.values(entry)[0]);

            // Crie um gráfico de barras
            createBarChart('eventsByActionChart', 'Número de Eventos por Ação', labels, values);
        });


    fetch('/analises/eventos-por-id-do-estado')
        .then(response => response.json())
        .then(data => {
            // Crie um array de labels e um array de dados
            const labels = data.map(entry => Object.keys(entry)[0]);
            const values = data.map(entry => Object.values(entry)[0]);

            // Crie um gráfico de barras
            createPieChart('eventsByStateIdChart', 'Número de Eventos por Id do Estado', labels, values);
        });


    fetch('/analises/eventos-por-nome-do-estado')
        .then(response => response.json())
        .then(data => {
            // Crie um array de labels e um array de dados
            const labels = data.map(entry => Object.keys(entry)[0]);
            const values = data.map(entry => Object.values(entry)[0]);

            // Crie um gráfico de barras
            createPieChart('eventsByStateNameChart', 'Número de eventos por nome do estado', labels, values);
        });

    fetch('/analises/eventos-por-nome-do-estado-anterior')
        .then(response => response.json())
        .then(data => {
            // Crie um array de labels e um array de dados
            const labels = data.map(entry => Object.keys(entry)[0]);
            const values = data.map(entry => Object.values(entry)[0]);

            // Crie um gráfico de barras
            createPieChart('eventsByPreviousStateNameChart', 'Número de eventos por nome do estado anterior', labels, values);
        });


fetch('/analises/distribuicao-temporal')
    .then(response => response.json())
    .then(data => {
        // Transforme os dados em um formato adequado para gráfico de linha temporal
         const formattedData = data.map(entry => {
             const timestamp = Object.keys(entry)[0];             
             const value = Object.values(entry)[0];             
             return { x: new Date(timestamp).getTime(), y: value };
         });
     // Crie um gráfico de linha
        createLineChart('temporalDistributionChart', 'Distribuição Temporal dos Eventos', formattedData);
    });

});

// Função para criar um gráfico de barras
function createBarChart(chartId, chartTitle, labels, values) {
    const ctx = document.getElementById(chartId).getContext('2d');

    // Gerar cores aleatórias para cada barra
    const backgroundColors = generateRandomColors(labels.length);

    new Chart(ctx, {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [{
                label: chartTitle,
                data: values,
                backgroundColor: backgroundColors,
                borderColor: 'rgba(75, 192, 192, 1)',
                borderWidth: 1
            }]
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true
                }
            },
            plugins: {
                legend: {
                    display: false
                }
            }
        }
    });
}


// Função para gerar cores aleatórias
function generateRandomColors(count) {
    const colors = [];
    for (let i = 0; i < count; i++) {
        const color = `rgba(${Math.floor(Math.random() * 256)}, ${Math.floor(Math.random() * 256)}, ${Math.floor(Math.random() * 256)}, 0.2)`;
        colors.push(color);
    }
    return colors;
}


 function createLineChart(chartId, chartTitle, data) {
     const ctx = document.getElementById(chartId).getContext('2d');
     new Chart(ctx, {
         type: 'line',
         data: {
             datasets: [{
                 label: chartTitle,
                 data: data
             }]
         },
         options: {
             responsive: true,
             scales: {
                 x: {
                    type: 'linear',
                    // time: {
                    //     parser: 'YYYY-MM-DDTHH:mm:ss',
                    //     unit: 'minute',
                    //     displayFormats: {
                    //         'minute': 'YYYY-MM-DDTHH:mm:ss',
                    //         'hour': 'YYYY-MM-DDTHH:mm:ss'
                    //     }
                    // },
                    ticks: {
                        userCallback: function(t, i) {
                            // Mantenha apenas o valor do tick (i) como está
                            return i;
                        }
                    }
                }   
             }
         }
     });
 }





// Função para criar um gráfico de pizza
function createPieChart(chartId, chartTitle, labels, values) {
    const ctx = document.getElementById(chartId).getContext('2d');
    new Chart(ctx, {
        type: 'pie',
        data: {
            labels: labels,
            datasets: [{
                label: chartTitle,
                data: values,
                backgroundColor: [
                    'rgba(255, 99, 132, 0.5)',
                    'rgba(54, 162, 235, 0.5)',
                    'rgba(255, 206, 86, 0.5)',
                    'rgba(75, 192, 192, 0.5)',
                    'rgba(153, 102, 255, 0.5)',
                    'rgba(255, 159, 64, 0.5)'
                ],
                borderColor: [
                    'rgba(255, 99, 132, 1)',
                    'rgba(54, 162, 235, 1)',
                    'rgba(255, 206, 86, 1)',
                    'rgba(75, 192, 192, 1)',
                    'rgba(153, 102, 255, 1)',
                    'rgba(255, 159, 64, 1)'
                ],
                borderWidth: 1
            }]
        }
    });
}
