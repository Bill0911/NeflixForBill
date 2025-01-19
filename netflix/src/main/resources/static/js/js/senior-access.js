document.addEventListener("DOMContentLoaded", () => {
    const loadTableButton = document.getElementById("loadTable");
    const tableSelect = document.getElementById("tableSelect");
    const tableContainer = document.getElementById("tableContainer");
    const formContainer = document.getElementById("formContainer");
    const crudForm = document.getElementById("crudForm");
    const formFields = document.getElementById("formFields");

    if (!loadTableButton || !tableSelect || !tableContainer || !crudForm) {
        console.error("One or more required DOM elements are missing.");
        return;
    }

    loadTableButton.addEventListener("click", () => loadTableData(tableSelect.value));

    async function fetchAPI(url, method, data = null) {
        const headers = {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${localStorage.getItem("token")}`
        };
        const response = await fetch(url, {
            method: method,
            headers: headers,
            body: data ? JSON.stringify(data) : null
        });
        if (!response.ok) throw new Error(`HTTP error! Status: ${response.status}`);
        return response.json();
    }

    async function loadTableData(tableName) {
        tableContainer.innerHTML = "<p>Loading...</p>";
        try {
            const data = await fetchAPI(`http://localhost:8081/api/${tableName}`, "GET");
            console.log(data);
            renderTable(data, tableName);
        } catch (error) {
            console.error("Error loading table data:", error);
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
            const editButton = createActionButton('Edit', () => loadEditForm(item, tableName));
            const deleteButton = createActionButton('Delete', () => deleteRow(item.id, tableName));
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
        button.addEventListener('click', action);
        return button;
    }

    function loadEditForm(item, tableName) {
        crudForm.dataset.id = item.id;
        crudForm.dataset.table = tableName;
        formFields.innerHTML = "";
        Object.entries(item).forEach(([key, value]) => {
            const label = document.createElement('label');
            label.textContent = key;
            const input = document.createElement('input');
            input.type = key === 'id' ? 'text' : 'input'; // Adjust input types as necessary
            input.name = key;
            input.value = value;
            formFields.appendChild(label);
            formFields.appendChild(input);
        });
        const saveButton = createActionButton('Save', submitForm);
        formFields.appendChild(saveButton);
    }

    crudForm.addEventListener("submit", async (e) => {
        e.preventDefault();
        const data = Object.fromEntries(new FormData(crudForm));
        const method = crudForm.dataset.id ? 'PUT' : 'POST';
        const url = `http://localhost:8081/api/${crudForm.dataset.table}/${crudForm.dataset.id || ''}`;

        try {
            const result = await fetchAPI(url, method, data);
            alert('Operation successful!');
            loadTableData(crudForm.dataset.table);
        } catch (error) {
            console.error("Error saving entry:", error);
            alert(`Failed to save entry: ${error.message}`);
        }
    });

    async function deleteRow(id, tableName) {
        if (!confirm("Are you sure you want to delete this entry?")) return;

        try {
            await fetchAPI(`http://localhost:8081/api/${tableName}/${id}`, "DELETE");
            alert("Deleted successfully!");
            loadTableData(tableName);
        } catch (error) {
            console.error("Error deleting entry:", error);
            alert(`Failed to delete entry: ${error.message}`);
        }
    }
});
