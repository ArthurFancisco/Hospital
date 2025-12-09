document.addEventListener('DOMContentLoaded', function () {
    const selectElement = document.querySelector('select[name="tipoUsuario"]');
    const cpfField = document.getElementById('cpfField');
    const cpfInput = document.getElementById('cpfInput');

    function toggleCpfField() {
        const selectedValue = selectElement.value;
        
        // Exibe o campo CPF apenas para Pacientes, Médicos, e Funcionários
        if (['PACIENTE', 'MEDICO', 'FUNCIONARIO'].includes(selectedValue)) {
            cpfField.style.display = 'block';
            cpfInput.setAttribute('required', 'required'); // Torna o CPF obrigatório
        } else {
            cpfField.style.display = 'none';
            cpfInput.removeAttribute('required'); // Remove a obrigatoriedade
        }
    }

    // Inicializa e monitora a mudança
    toggleCpfField(); 
    selectElement.addEventListener('change', toggleCpfField);
});