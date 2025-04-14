
function generateTimeOptions(startTime, endTime) {
    const options = [];
    for (let hour = startTime; hour <= endTime; hour++) {
        for (let minute = 0; minute < 60; minute++) {
            const time = `${hour.toString().padStart(2, '0')}:${minute.toString().padStart(2, '0')}`;
            options.push(time);
        }
    }
    return options;
}

function fetchBookedTimes() {
    const selectedDate = $('#date').val();
    const hourSelect = $('#hour');
    const minuteSelect = $('#minute');

    if (!selectedDate) return;

    $.ajax({
        url: '/api/booked-times',
        type: 'GET',
        data: { date: selectedDate },
        success: function(bookedTimes) {
            // Clear existing options
            hourSelect.empty();
            minuteSelect.empty();

            // Add default options
            hourSelect.append('<option value="">Hour</option>');
            minuteSelect.append('<option value="">Minute</option>');

            // Create a map of booked times for easier checking
            const bookedTimesMap = new Map();
            bookedTimes.forEach(time => {
                const [hours, minutes] = time.split(':');
                if (!bookedTimesMap.has(hours)) {
                    bookedTimesMap.set(hours, new Set());
                }
                bookedTimesMap.get(hours).add(minutes);
            });

            // Generate hours (9 AM to 5 PM)
            for (let hour = 9; hour <= 17; hour++) {
                const hourValue = hour.toString().padStart(2, '0');
                const option = $('<option>').val(hourValue).text(hourValue);
                
                // Check if all minutes in this hour are booked
                const bookedMinutesInHour = bookedTimesMap.get(hourValue) || new Set();
                if (bookedMinutesInHour.size < 60) {
                    // Only add the hour if there are available minutes
                    hourSelect.append(option);
                }
            }

            // Generate minutes (00 to 59)
            for (let minute = 0; minute < 60; minute++) {
                const minuteValue = minute.toString().padStart(2, '0');
                const option = $('<option>').val(minuteValue).text(minuteValue);
                minuteSelect.append(option);
            }

            // Update minute options when hour is selected
            $('#hour').on('change', function() {
                const selectedHour = $(this).val();
                if (!selectedHour) {
                    minuteSelect.find('option').prop('disabled', false);
                    return;
                }

                const bookedMinutesInHour = bookedTimesMap.get(selectedHour) || new Set();
                minuteSelect.find('option').each(function() {
                    const minuteValue = $(this).val();
                    if (minuteValue && bookedMinutesInHour.has(minuteValue)) {
                        $(this).prop('disabled', true);
                    } else {
                        $(this).prop('disabled', false);
                    }
                });
            });
        },
        error: function(error) {
            console.error('Error fetching booked times:', error);
        }
    });
}

function updateTimeField() {
    const hour = $('#hour').val();
    const minute = $('#minute').val();
    if (hour && minute) {
        $('#time').val(`${hour}:${minute}`);
    } else {
        $('#time').val('');
    }
}

function validateStudentId() {
    const studentId = $('#studentId');
    const studentIdValue = studentId.val();
    const formGroup = studentId.closest('.form-group');
    const errorMessage = formGroup.find('.error-message');

  
    const cleanValue = studentIdValue.replace(/[^A-Z0-9]/g, '').toUpperCase();
    
    
    if (cleanValue !== studentIdValue) {
        studentId.val(cleanValue);
    }

    // Validate length
    if (cleanValue.length > 11) {
        formGroup.addClass('error');
        errorMessage.text('Student ID must be 11 characters or less');
        return false;
    }

    // Validate format (only letters and numbers)
    if (cleanValue && !/^[A-Z0-9]+$/.test(cleanValue)) {
        formGroup.addClass('error');
        errorMessage.text('Student ID can only contain letters and numbers');
        return false;
    }

    formGroup.removeClass('error');
    errorMessage.text('');
    return true;
}

$(document).ready(function() {
    $('#date').on('change', fetchBookedTimes);
    $('#hour, #minute').on('change', updateTimeField);

    // Add student ID validation
    $('#studentId').on('input', function() {
        validateStudentId();
    });

    // Validate form submission
    $('form').on('submit', function(e) {
        if (!validateStudentId()) {
            e.preventDefault();
        }
    });
});
