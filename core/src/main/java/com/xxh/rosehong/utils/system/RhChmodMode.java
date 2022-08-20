package com.xxh.rosehong.utils.system;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/8/18 22:18
 */

/**
 * 具体可查看 https://man7.org/linux/man-pages/man2/chmod.2.html
 */
public interface RhChmodMode {

    // 特殊权限（一般用不到）
    int S_ISUID = 04000;
    int S_ISGID = 02000;
    int S_ISVTX = 01000;

    // owner rwx
    int S_IRUSR = 00400;
    int S_IWUSR = 00200;
    int S_IXUSR = 00100;

    // group rwx
    int S_IRGRP = 00040;
    int S_IWGRP = 00020;
    int S_IXGRP = 00010;

    // other rwx
    int S_IROTH = 00004;
    int S_IWOTH = 00002;
    int S_IXOTH = 00001;

    int MODE_777 = S_IRUSR | S_IWUSR | S_IXUSR
            | S_IRGRP | S_IWGRP | S_IXGRP
            | S_IROTH | S_IWOTH | S_IXOTH;
}
