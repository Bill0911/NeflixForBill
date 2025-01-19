document.addEventListener("DOMContentLoaded", () => {
    const loadTableButton = document.getElementById("loadTable");
    const tableSelect = document.getElementById("tableSelect");
    const tableContainer = document.getElementById("tableContainer");
    const crudForm = document.getElementById("crudForm");
    const formFields = document.getElementById("formFields");

    if (!loadTableButton || !tableSelect || !tableContainer || !crudForm) {
        console.error("One or more required DOM elements are missing.");
        return;
    }

    loadTableButton.addEventListener("click", () => loadTableData(tableSelect.value));

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

    async function fetchAPI(url, method, data = null) {
        const headers = new Headers({
            "Content-Type": "application/json",
            "Authorization": `Bearer ${localStorage.getItem("token")}`
        });

        const fetchOptions = {
            method: method,
            headers: headers
        };

        if (data && method !== "GET" && method !== "DELETE") {
            fetchOptions.body = JSON.stringify(data);
        }

        try {
            const response = await fetch(url, fetchOptions);
            if (!response.ok) {
                const errorText = await response.text();
                throw new Error(`HTTP error! Status: ${response.status}: ${errorText}`);
            }
            return method === "DELETE" ? response.text() : response.json();
        } catch (error) {
            throw error;
        }
    }

    async function loadTableData(tableName) {
        tableContainer.innerHTML = "<p>Loading...</p>";
        try {
            const data = await fetchAPI(`http://localhost:8081/api/${tableName}`, "GET");
            renderTable(data, tableName);
        } catch (error) {
            tableContainer.innerHTML = `<p>Error loading table data: ${error.message}</p>`;
        }
    }

    function renderTable(data, tableName) {
        tableContainer.innerHTML = "";
        if (!data || data.length === 0) {
            tableContainer.innerHTML = `<p>No data available for table "${tableName}".</p>`;
            return;
        }

        const table = document.createElement("table");
        const headerRow = document.createElement("tr");
        Object.keys(data[0]).forEach(key => {
            const th = document.createElement("th");
            th.textContent = key;
            headerRow.appendChild(th);
        });
        const actionTh = document.createElement("th");
        actionTh.textContent = "Actions";
        headerRow.appendChild(actionTh);
        table.appendChild(headerRow);

        data.forEach(item => {
            const tr = document.createElement("tr");
            Object.entries(item).forEach(([key, value]) => {
                const td = document.createElement("td");
                td.textContent = value;
                tr.appendChild(td);
            });

            const actionTd = document.createElement("td");
            const idFieldName = getIdFieldName(tableName); // Get the identifier field name dynamically
            const editButton = createActionButton('Edit', () => loadEditForm(item, tableName));
            const deleteButton = createActionButton('Delete', () => deleteRow(item[idFieldName], tableName));
            actionTd.appendChild(editButton);
            actionTd.appendChild(deleteButton);
            tr.appendChild(actionTd);

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

    function loadEditForm(item, tableName) {
        const idFieldName = getIdFieldName(tableName);
        crudForm.dataset.id = item[idFieldName];
        crudForm.dataset.table = tableName;
        formFields.innerHTML = "";
        Object.entries(item).forEach(([key, value]) => {
            const label = document.createElement('label');
            label.textContent = key;
            const input = document.createElement('input');
            input.type = 'text';
            input.name = key;
            input.value = value;
            formFields.appendChild(label);
            formFields.appendChild(input);
        });
    }

    crudForm.addEventListener("submit", async (e) => {
        e.preventDefault();
        const id = crudForm.dataset.id;
        const tableName = crudForm.dataset.table;
        const data = Object.fromEntries(new FormData(crudForm));
        const url = `http://localhost:8081/api/${tableName}/${id}`;
        const method = id ? 'PUT' : 'POST';

        try {
            await fetchAPI(url, method, data);
            alert('Operation successful!');
            loadTableData(tableName);
        } catch (error) {
            console.error("Error saving entry:", error);
            alert(`Failed to save entry: ${error.message}`);
        }
    });

    async function deleteRow(id, tableName) {
        if (!id) {
            console.error("Attempted to delete an item with undefined ID.");
            alert("Cannot delete this item due to a missing ID.");
            return;
        }
        if (!confirm("Are you sure you want to delete this entry?")) return;
        const url = `http://localhost:8081/api/${tableName}/${id}`;
        try {
            await fetchAPI(url, "DELETE");
            alert("Deleted successfully!");
            loadTableData(tableName);
        } catch (error) {
            console.error("Error deleting entry:", error);
            alert(`Failed to delete entry: ${error.message}`);
        }
    }
});
