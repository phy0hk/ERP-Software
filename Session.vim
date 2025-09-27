let SessionLoad = 1
let s:so_save = &g:so | let s:siso_save = &g:siso | setg so=0 siso=0 | setl so=-1 siso=-1
let v:this_session=expand("<sfile>:p")
silent only
silent tabonly
cd ~/Programming/ERP-Software
if expand('%') == '' && !&modified && line('$') <= 1 && getline(1) == ''
  let s:wipebuf = bufnr('%')
endif
let s:shortmess_save = &shortmess
if &shortmess =~ 'A'
  set shortmess=aoOA
else
  set shortmess=aoO
endif
badd +174 Inventory\ And\ Supplier\ Management/inv_sup_management_ui/src/components/common/table.tsx
badd +49 Inventory\ And\ Supplier\ Management/inv_sup_management_ui/src/pages/inventory.tsx
badd +48 Inventory\ And\ Supplier\ Management/inv_sup_management_ui/src/utils/TypesList.ts
badd +21 Inventory\ And\ Supplier\ Management/inv_sup_management_ui/src/components/inventory/WarehouseSideBar.tsx
badd +91 Inventory\ And\ Supplier\ Management/inv_sup_management/src/main/java/com/erpsoftware/inv_sup_management/controllers/LocationController.java
badd +9 Inventory\ And\ Supplier\ Management/inv_sup_management_ui/src/utils/APILinks.ts
argglobal
%argdel
edit Inventory\ And\ Supplier\ Management/inv_sup_management_ui/src/components/common/table.tsx
let s:save_splitbelow = &splitbelow
let s:save_splitright = &splitright
set splitbelow splitright
wincmd _ | wincmd |
vsplit
1wincmd h
wincmd w
wincmd _ | wincmd |
split
1wincmd k
wincmd w
let &splitbelow = s:save_splitbelow
let &splitright = s:save_splitright
wincmd t
let s:save_winminheight = &winminheight
let s:save_winminwidth = &winminwidth
set winminheight=0
set winheight=1
set winminwidth=0
set winwidth=1
exe 'vert 1resize ' . ((&columns * 30 + 136) / 272)
exe '2resize ' . ((&lines * 43 + 34) / 68)
exe 'vert 2resize ' . ((&columns * 241 + 136) / 272)
exe '3resize ' . ((&lines * 21 + 34) / 68)
exe 'vert 3resize ' . ((&columns * 241 + 136) / 272)
argglobal
enew
file NvimTree_1
balt Inventory\ And\ Supplier\ Management/inv_sup_management_ui/src/components/common/table.tsx
setlocal foldmethod=manual
setlocal foldexpr=0
setlocal foldmarker={{{,}}}
setlocal foldignore=#
setlocal foldlevel=0
setlocal foldminlines=1
setlocal foldnestmax=20
setlocal nofoldenable
wincmd w
argglobal
setlocal foldmethod=manual
setlocal foldexpr=0
setlocal foldmarker={{{,}}}
setlocal foldignore=#
setlocal foldlevel=0
setlocal foldminlines=1
setlocal foldnestmax=20
setlocal foldenable
silent! normal! zE
let &fdl = &fdl
let s:l = 165 - ((30 * winheight(0) + 21) / 43)
if s:l < 1 | let s:l = 1 | endif
keepjumps exe s:l
normal! zt
keepjumps 165
normal! 059|
wincmd w
argglobal
if bufexists(fnamemodify("term://~/Programming/ERP-Software//3060:/bin/zsh", ":p")) | buffer term://~/Programming/ERP-Software//3060:/bin/zsh | else | edit term://~/Programming/ERP-Software//3060:/bin/zsh | endif
if &buftype ==# 'terminal'
  silent file term://~/Programming/ERP-Software//3060:/bin/zsh
endif
balt Inventory\ And\ Supplier\ Management/inv_sup_management_ui/src/components/common/table.tsx
setlocal foldmethod=manual
setlocal foldexpr=0
setlocal foldmarker={{{,}}}
setlocal foldignore=#
setlocal foldlevel=0
setlocal foldminlines=1
setlocal foldnestmax=20
setlocal foldenable
let s:l = 53 - ((19 * winheight(0) + 10) / 21)
if s:l < 1 | let s:l = 1 | endif
keepjumps exe s:l
normal! zt
keepjumps 53
normal! 03|
wincmd w
3wincmd w
exe 'vert 1resize ' . ((&columns * 30 + 136) / 272)
exe '2resize ' . ((&lines * 43 + 34) / 68)
exe 'vert 2resize ' . ((&columns * 241 + 136) / 272)
exe '3resize ' . ((&lines * 21 + 34) / 68)
exe 'vert 3resize ' . ((&columns * 241 + 136) / 272)
tabnext 1
if exists('s:wipebuf') && len(win_findbuf(s:wipebuf)) == 0 && getbufvar(s:wipebuf, '&buftype') isnot# 'terminal'
  silent exe 'bwipe ' . s:wipebuf
endif
unlet! s:wipebuf
set winheight=1 winwidth=20
let &shortmess = s:shortmess_save
let &winminheight = s:save_winminheight
let &winminwidth = s:save_winminwidth
let s:sx = expand("<sfile>:p:r")."x.vim"
if filereadable(s:sx)
  exe "source " . fnameescape(s:sx)
endif
let &g:so = s:so_save | let &g:siso = s:siso_save
set hlsearch
nohlsearch
doautoall SessionLoadPost
unlet SessionLoad
" vim: set ft=vim :
