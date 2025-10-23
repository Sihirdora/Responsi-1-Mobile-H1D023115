# Responsi-1-Mobile
# Feidinata Artandi - H1D023115 - D - B

https://github.com/user-attachments/assets/647c9d8e-10c3-4274-abda-d3107b43317c


PENJELASAN: 

1. Meminta Data ke "Dapur" API (The Call) 📞
Semua dimulai ketika klik menu Head Coach atau Team Squad. Ini memicu fungsi di dalam Fragment, misalnya fetchTeamSquad().

Pesan di Dapur Belakang: Karena kita tidak mau aplikasi hang (ANR), kode langsung bilang ke Kotlin Coroutines untuk jalan di dapur belakang (Dispatchers.IO).

Mengirim Kurir: Coroutines mengutus Retrofit sebagai kurir. Kurir Retrofit mengambil alamat tujuan (URL API) dan membawa Token API kita sebagai kunci otorisasi.

Verifikasi Token: Retrofit mengirimkan permintaan ke server football-data.org. Server memverifikasi token dan mengirim balik data lengkap Leicester City dalam format JSON.

2. Mengolah Data (The Processing) 🥣
Data JSON yang datang itu masih mentah. Kita harus mengolahnya menjadi sesuatu yang bisa dibaca Kotlin.

Membentuk Objek: Di sini peran GSON (yang bekerja di balik Retrofit). GSON mengambil JSON mentah dan langsung mengubahnya menjadi objek Kotlin yang terstruktur sesuai data class kita: TeamResponse (data tim) dan SquadMember (data per individu).

Menyaring Pesanan: Setelah data jadi objek Kotlin, kita jalankan Filter kita di Fragment. Misalnya:

Di Squad Teams, kita menyaring 29 anggota untuk mengecualikan Pelatih/Staf (yang tidak memiliki position yang jelas).

Di Head Coach, kita mencari role Pelatih/Manajer yang paling senior.

Logika Pewarnaan: Di adapter, kita menjalankan logika when untuk menentukan warna kartu (yellow_goalkeeper, blue_defense, dst.) berdasarkan string position yang diterima dari API. Jika posisi tidak jelas, kita berikan warna secara random agar tidak ada kartu putih.

3. Menyajikan ke Meja (The Display) 🖼️
Data yang sudah bersih dan terolah harus ditampilkan di layar.

Pindah ke Depan: Kode Coroutines kembali ke Main Thread (Dispatchers.Main). Ini penting, karena semua yang dilihat pengguna harus diperbarui di Main Thread.

Mengisi Tampilan:

Jika Head Coach, data yang sudah disaring langsung mengisi TextView (coachName, coachDob) dan ImageView (coachPhoto).

Jika Team Squad, List pemain diserahkan ke PlayerAdapter. Adapter membuat tampilan kartu (CardView) untuk setiap pemain dan menempelkannya ke RecyclerView.
