function initTooltip ()
{
    const tooltipTriggerList = document.querySelectorAll('[data-bs-toggle="tooltip"]')
    const tooltipList = [...tooltipTriggerList].map(tooltipTriggerEl => new bootstrap.Tooltip(tooltipTriggerEl))
}
window.onload = initTooltip;

function onlyNumberInput(event)
{
    let asciiCode = event.which? event.which : event.keyCode;
    if (31 < asciiCode && (asciiCode < 48 || 57 < asciiCode))
        return false;
    else
        return true;
}