# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

# Unable to find any files that looked like license statements. Check the accompanying
# documentation and source headers and set LICENSE and LIC_FILES_CHKSUM accordingly.
#
# NOTE: LICENSE is being set to "CLOSED" to allow you to at least start building - if
# this is not accurate with respect to the licensing of the software being built (it
# will not be in most cases) you must specify the correct value before using this
# recipe for anything other than initial testing/development!
LICENSE = "CLOSED"
LIC_FILES_CHKSUM = ""

SRC_URI = "git://github.com/cu-ecen-aeld/assignments-3-and-later-zakimadaoui.git;protocol=ssh;branch=main"
# SRC_URI = "git:///home/mzak6762/assignments-3-and-later-zakimadaoui;protocol=file;branch=main"

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "e3e4083dfa26299d879d2cd309ae162673e23756"

S = "${WORKDIR}/git/aesd-char-driver"

inherit module

EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"


# Inherit statup script utilities class and provide necessary config
inherit update-rc.d
# Flag this package as one that uses init scripts
INITSCRIPT_PACKAGES="${PN}"
INITSCRIPT_NAME:${PN}= "aesd-start-stop" 

do_install:append() {
    install -d ${D}${INIT_D_DIR}
    install -m 0755 ${S}/aesd-start-stop    ${D}${INIT_D_DIR}/aesd-start-stop
    install -m 0755 ${S}/aesdchar_load      ${D}${INIT_D_DIR}/aesdchar_load
    install -m 0755 ${S}/aesdchar_unload    ${D}${INIT_D_DIR}/aesdchar_unload

}

FILES:${PN} += "\
        ${INIT_D_DIR}/aesd-start-stop \
        ${INIT_D_DIR}/aesdchar_load \
        ${INIT_D_DIR}/aesdchar_unload \
"