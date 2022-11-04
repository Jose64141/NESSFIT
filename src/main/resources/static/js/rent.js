let allDates = document.getElementsByClassName("date-label");
const selectedDates = Array(allDates.length).fill(false);

function dateButton(event)
{
    var buttonNumber = parseInt(event.currentTarget.id);
    selectedDates[buttonNumber] = !selectedDates[buttonNumber];
    if(selectedDates[buttonNumber])
    {
        event.currentTarget.className = "activated"
        document.getElementById("send-button").style.display = "block";
    }
    else
    {
        event.currentTarget.className = "deactivated"
        for (const date of selectedDates)
        {
            if(date) return;
        }
        document.getElementById("send-button").style.display = "none";
    }

}
function confirmButton(event)
{
    var pricePerDay = parseInt(document.getElementById("price").value.replace("$",""));
    var days = 0;
    selectedDates.forEach( (item) => { if(item) { days++;} })
    var totalPrice = "$" + toString(days*pricePerDay);
    document.getElementById("total").value = totalPrice;
    document.getElementById("confirm-total").value = totalPrice;
    var datesForm = "";
    allDates.forEach( (item, index) =>
    {
        if(selectedDates[index])
        {
            var dateEntry = "";
            if(index != 0){ dateEntry = "," }
            dateEntry.concat(item.value);
            datesForm.concat(dateEntry);
        }
    })
    document.getElementById("dates").value = datesForm;
}

for (let button of document.getElementsByClassName("date-button"))
{
    button.onclick = dateButton;
}
document.getElementById("send-button").onclick = confirmButton;