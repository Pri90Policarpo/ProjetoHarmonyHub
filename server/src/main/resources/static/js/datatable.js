    // Função para preencher a tabela com dados da API
    function fillTable(data) {
      const dataTable = $('#dataTable').DataTable({
        destroy: true, // Destruir a tabela existente antes de criar uma nova
        data: data,
        columns: [
          { data: 'storageDate', render: function(data) { return new Date(data).toLocaleString(); } },
          { data: 'category' },
          { data: 'action' },
          { data: 'stateName' },
          { data: 'stateId' },
          { data: 'messageId' },
          { data: 'previousStateId' },
          { data: 'previousStateName' }
        ]
      });
    }

    // Função para buscar dados da API
    function fetchData() {
      fetch('/blip/all-data')
        .then(response => response.json())
        .then(data => {
          // Preencher a tabela
          fillTable(data);
        })
        .catch(error => console.error('Erro ao buscar dados da API:', error));
    }

    // Chamar a função para buscar dados da API ao carregar a página
    fetchData();