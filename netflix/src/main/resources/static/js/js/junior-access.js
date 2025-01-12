document.getElementById('loadTable').addEventListener('click', async () => {
    const tableName = document.getElementById('tableSelect').value;
    const tableContainer = document.getElementById('tableContainer');

    try {
        const response = await fetch(`http://localhost:8081/api/${tableName}`);
        if (!response.ok) {
            throw new Error(`Failed to fetch data for table: ${tableName}`);
        }

        const data = await response.json();

        // Clear previous table content
        tableContainer.innerHTML = '';

        // Build table dynamically
        const table = document.createElement('table');
        const headerRow = document.createElement('tr');

        // Generate headers
        Object.keys(data[0]).forEach((key) => {
            const th = document.createElement('th');
            th.textContent = key;
            headerRow.appendChild(th);
        });

        // Add action column
        const actionTh = document.createElement('th');
        actionTh.textContent = 'Actions';
        headerRow.appendChild(actionTh);

        table.appendChild(headerRow);

        // Generate rows
        data.forEach((row) => {
            const tr = document.createElement('tr');
            Object.values(row).forEach((value) => {
                const td = document.createElement('td');
                td.textContent = value;
                tr.appendChild(td);
            });

            // Add edit and delete buttons
            const actionTd = document.createElement('td');
            const editButton = document.createElement('button');
            editButton.textContent = 'Edit';
            editButton.addEventListener('click', () => loadFormData(row));
            const deleteButton = document.createElement('button');
            deleteButton.textContent = 'Delete';
            deleteButton.addEventListener('click', () => deleteRow(row.id, tableName));

            actionTd.appendChild(editButton);
            actionTd.appendChild(deleteButton);
            tr.appendChild(actionTd);

            table.appendChild(tr);
        });

        tableContainer.appendChild(table);
    } catch (error) {
        console.error('Error loading table:', error);
    }
});
