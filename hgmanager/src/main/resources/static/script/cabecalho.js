const menu = document.querySelector('.menu');
const nav = document.querySelector('nav');
const dropdown = document.getElementById('dropdown');
const dropdownContent = document.getElementById('dropdown-content');

// Toggle the navigation menu for mobile
menu.addEventListener('click', () => {
    nav.classList.toggle('active');
});

// Dropdown hover effect
dropdown.addEventListener('mouseover', () => {
    dropdownContent.style.display = 'block';
});

dropdown.addEventListener('mouseout', () => {
    dropdownContent.style.display = 'none';
});
