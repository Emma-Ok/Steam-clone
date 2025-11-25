import { MainLayout } from '@/src/design-system/templates/main-layout';
import { AuthPage } from '@/src/design-system/pages/auth-page';

export default function RegisterPage() {
  return (
    <MainLayout>
      <AuthPage mode="register" />
    </MainLayout>
  );
}
