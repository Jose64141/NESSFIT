let allDates = document.getElementsByClassName("date-label");
const selectedDates = Array(allDates.length).fill(false);

function dateButton(event, id)
{
    let buttonNumber = parseInt(event.currentTarget.id);
    selectedDates[buttonNumber] = !selectedDates[buttonNumber];
    if(selectedDates[buttonNumber])
    {
        // https://www.delftstack.com/howto/javascript/change-the-class-of-an-element-with-javascript/#:~:text=Change%20the%20Class%20of%20an%20HTML%20Element%20With,Been%20Used%20in%20an%20Element%20in%20JavaScript%20
        event.currentTarget.firstElementChild.className = "btn btn-primary h-100 w-100";
        document.getElementById("send-button").className = "send-button-active" +
            " position-absolute bottom-0 end-0 m-5";
    }
    else
    {
        event.currentTarget.firstElementChild.className = "btn btn-light h-100 w-100";
        for (const date of selectedDates)
        {
            if(date) return;
        }
        document.getElementById("send-button").className = "send-button-inactive" +
            " position-absolute bottom-0 end-0 m-5";
    }

}
function confirmButton(event)
{
    let pricePerDay = parseInt(document.getElementById("price").textContent);
    let days = 0;
    let totalPrice = 0;
    let datesForm = "";
    let count = 0;

    selectedDates.forEach( (item) => { if(item) { days++;} })
    totalPrice = days*pricePerDay;
    document.getElementById("total").value = totalPrice;
    document.getElementById("confirm-total").textContent = "$" + totalPrice.toString();

    for(var i = 0; i < allDates.length; i++)
    {
        if(selectedDates[i])
        {
            let date = allDates.item(i);
            let dateEntry = "";
            if(count !== 0){ dateEntry = "," }
            dateEntry += date.textContent;
            datesForm += dateEntry;
            count++;
        }
    }
    document.getElementById("dates").value = datesForm;
}