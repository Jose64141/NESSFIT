function onlyNumberInput(event)
{
    let asciiCode = event.which? event.which : event.keyCode;
    if (31 < asciiCode && (asciiCode < 48 || 57 < asciiCode))
        return false;
    else
        return true;
}