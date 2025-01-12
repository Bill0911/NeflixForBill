document.getElementById('load-data').addEventListener('click', async () => {
    const tableName = document.getElementById('table-select').value;
    const dataTable = document.getElementById('data-table');
    const token = localStorage.getItem('jwtToken'); // Replace with actual token storage method

    dataTable.innerHTML = '<p>Loading data...</p>';

    try {
        const response = await fetch(`http://localhost:8081/api/${tableName}`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json',
            },
        });

        if (!response.ok) {
            throw new Error(`Failed to load data: ${response.statusText}`);
        }

        const data = await response.json();
        dataTable.innerHTML = generateTable(data);
    } catch (error) {
        console.error('Error fetching data:', error);
        dataTable.innerHTML = `<p>Error loading data: ${error.message}</p>`;
    }
});

function generateTable(data) {
    if (!Array.isArray(data) || data.length === 0) {
        return '<p>No data available.</p>';
    }

    const tableHeaders = Object.keys(data[0])
        .map(key => `<th>${key}</th>`)
        .join('');
    const tableRows = data
        .map(row => `<tr>${Object.values(row).map(value => `<td>${value}</td>`).join('')}</tr>`)
        .join('');

    return `
    <table>
      <thead>
        <tr>${tableHeaders}</tr>
      </thead>
      <tbody>
        ${tableRows}
      </tbody>
    </table>
  `;
}
