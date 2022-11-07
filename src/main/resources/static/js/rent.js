let allDates = document.getElementsByClassName("date-label");
const selectedDates = Array(allDates.length).fill(false);

function dateButton(event)
{
    var buttonNumber = parseInt(event.currentTarget.id);
    selectedDates[buttonNumber] = !selectedDates[buttonNumber];
    if(selectedDates[buttonNumber])
    {
        // https://www.delftstack.com/howto/javascript/change-the-class-of-an-element-with-javascript/#:~:text=Change%20the%20Class%20of%20an%20HTML%20Element%20With,Been%20Used%20in%20an%20Element%20in%20JavaScript%20
        event.currentTarget.firstElementChild.className = "btn btn-primary h-100 w-100";
        document.getElementById("send-button").style.display = "block";
    }
    else
    {
        event.currentTarget.firstElementChild.className = "btn btn-light h-100 w-100";
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
