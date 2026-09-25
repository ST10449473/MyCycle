namespace MyCycleAPI.Models
{
    public class PeriodEntry
    {
        public int Id { get; set; }

        public int UserId { get; set; }

        public DateTime StartDate { get; set; }

        public DateTime EndDate { get; set; }
    }
}