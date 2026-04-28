/* File: scripts.js */
document.addEventListener('DOMContentLoaded', function(){
  // Simple carousel
  const slides = [
    {img:'assets/hero1.jpg', title:'TMG Live: Breaking News', desc:'Stay updated with live coverage and top stories.'},
    {img:'assets/hero2.jpg', title:'Prime Time Shows', desc:'Catch our flagship programs and exclusive interviews.'},
    {img:'assets/hero3.jpg', title:'Sports Roundup', desc:'Highlights, analysis and live scores.'}
  ];
  let idx = 0;
  const imgEl = document.querySelector('.carousel img');
  const capTitle = document.querySelector('.carousel .caption .title');
  const capDesc = document.querySelector('.carousel .caption .desc');
  const dots = document.querySelectorAll('.carousel .dot');

  function render(){
    const s = slides[idx];
    imgEl.src = s.img;
    capTitle.textContent = s.title;
    capDesc.textContent = s.desc;
    dots.forEach((d,i)=> d.classList.toggle('active', i===idx));
  }
  if(imgEl){
    render();
    setInterval(()=>{ idx = (idx+1)%slides.length; render(); }, 5000);
    dots.forEach((d,i)=> d.addEventListener('click', ()=>{ idx=i; render(); }));
  }

  // Contact form simple client-side validation
  const contactForm = document.getElementById('contactForm');
  if(contactForm){
    contactForm.addEventListener('submit', function(e){
      const email = contactForm.querySelector('input[name="email"]').value.trim();
      const name = contactForm.querySelector('input[name="name"]').value.trim();
      if(!name || !email){
        e.preventDefault();
        alert('Please enter your name and email.');
      }
    });
  }
});
