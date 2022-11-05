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
    var pricePerDay = parseInt(document.getElementById("price").textContent);
    var days = 0;
    selectedDates.forEach( (item) => { if(item) { days++;} })
    var totalPrice = days*pricePerDay;
    document.getElementById("total").value = totalPrice;
    document.getElementById("confirm-total").textContent = "$" + totalPrice.toString();
    var datesForm = "";
    var count = 0;
    for(var i = 0; i < allDates.length; i++)
    {
        if(selectedDates[i])
        {
            var date = allDates.item(i);
            var dateEntry = "";
            if(count != 0){ dateEntry = "," }
            dateEntry += date.textContent;
            datesForm += dateEntry;
            count++;
        }
    }
    document.getElementById("dates").value = datesForm;
}
