document.addEventListener("DOMContentLoaded", () => {
    const tableSelect = document.getElementById("tableSelect");
    const loadTableButton = document.getElementById("loadTable");
    const tableContainer = document.getElementById("tableContainer");

    function getIdFieldName(tableName) {
        const idFieldNames = {
            users: 'accountId',
            movies: 'movieId',
            episodes: 'episodeId',
            genres: 'genreId',
            languages: 'languageId',
            payments: 'paymentId',
            series: 'seriesId',
            invitations: 'invitationId',
            profiles: 'profileId'
        };
        return idFieldNames[tableName] || 'id';
    }

    function isSensitiveInformation(tableName, key) {
        // Add sensitive tables and their sensitive fields
        const sensitiveData = {
            payments: ['amount', 'paymentMethod'],
        };
        return sensitiveData[tableName] && sensitiveData[tableName].includes(key);
    }

    loadTableButton.addEventListener("click", () => loadTableData(tableSelect.value));

    async function loadTableData(tableName) {
        tableContainer.innerHTML = "<p>Loading...</p>";
        const response = await fetch(`http://localhost:8081/api/${tableName}`, {
            method: "GET",
            headers: { "Authorization": `Bearer ${localStorage.getItem("token")}` }
        });
        if (response.ok) {
            const data = await response.json();
            renderTable(data, tableName);
        } else {
            tableContainer.innerHTML = "<p>Error loading data. Please check permissions.</p>";
        }
    }

    function renderTable(data, tableName) {
        tableContainer.innerHTML = "";
        if (data.length === 0) {
            tableContainer.innerHTML = "<p>No data available.</p>";
            return;
        }

        const table = document.createElement("table");
        const headerRow = document.createElement("tr");
        Object.keys(data[0]).forEach(key => {
            if (!isSensitiveInformation(tableName, key)) {
                const th = document.createElement("th");
                th.textContent = key;
                headerRow.appendChild(th);
            }
        });

        const actionTh = document.createElement("th");
        actionTh.textContent = "Actions";
        headerRow.appendChild(actionTh);
        table.appendChild(headerRow);

        data.forEach(item => {
            const tr = document.createElement("tr");
            Object.entries(item).forEach(([key, value]) => {
                if (!isSensitiveInformation(tableName, key)) {
                    const td = document.createElement("td");
                    td.textContent = value;
                    tr.appendChild(td);
                }
            });

            const actionsTd = document.createElement("td");
            const editButton = createActionButton('Edit', () => editItem(item, tableName));
            const deleteButton = createActionButton('Delete', () => deleteItem(item[getIdFieldName(tableName)], tableName));
            actionsTd.appendChild(editButton);
            actionsTd.appendChild(deleteButton);
            tr.appendChild(actionsTd);

            table.appendChild(tr);
        });

        tableContainer.appendChild(table);
    }

    function createActionButton(text, action) {
        const button = document.createElement("button");
        button.textContent = text;
        button.onclick = action;
        return button;
    }

    async function editItem(item, tableName) {
        console.log("Edit Item:", item);
        // Implement editing logic here or open an edit form
    }

    async function deleteItem(id, tableName) {
        if (!confirm("Are you sure you want to delete this entry?")) return;
        const response = await fetch(`http://localhost:8081/api/${tableName}/${id}`, {
            method: "DELETE",
            headers: { "Authorization": `Bearer ${localStorage.getItem("token")}` }
        });
        if (response.ok) {
            alert("Deleted successfully!");
            loadTableData(tableName);
        } else {
            const errorText = await response.text();
            alert(`Failed to delete: ${errorText}`);
        }
    }
});
